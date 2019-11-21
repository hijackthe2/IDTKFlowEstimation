package com.example.idtk.service;

import com.example.idtk.dao.DataStatisticDao;
import com.example.idtk.dao.DeviceInfoDao;
import com.example.idtk.domain.DataStatistic;
import com.example.idtk.domain.DeviceInfo;
import com.example.idtk.model.ReceiveModel;
import com.example.idtk.util.Crc16Utils;
import com.example.idtk.util.DateUtils;
import com.example.idtk.util.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class IDTKService {

    private static Logger logger = Logger.getLogger(IDTKService.class);
    private static final Time OPEN_TIME = DateUtils.parseTime("0800", "HHmm");//8:00
    private static final Time CLOSE_TIME = DateUtils.parseTime("2230", "HHmm");//22:30

    @Autowired
    private DeviceInfoDao deviceInfoDao;

    @Autowired
    private DataStatisticDao dataStatisticDao;

    @Transactional
    public String receiveData(ReceiveModel model) {
        logger.info("IDTK receive ====>" + model.toString());
        String ret = "";
        if (model.getCmd() == null)
            return "result=00";
        String cmd =model.getCmd();
        switch (cmd) {
            case "getsetting":
                // 设备被重新进行网络设定时或重新开机发送请求
                ret = getSetting(model.getData().get(0), model.getFlag());
                break;
            case "cache":
                // 上传设备缓存数据
                ret = cacheData(model);
                break;
            case "video":
//            ret = videoData(map.get("data").toString());
                logger.info("IDTK receive ====> 该参数暂不支持");
                break;
        }
        logger.info("IDTK return ====>" + ret);
        return "result=" + ret;
    }

    private String cacheData(ReceiveModel model) {
        String flag = model.getFlag();
        String status = model.getStatus();
        String countStr = model.getCount();
        logger.info("~~接收到设备发来的数据~~ flag:" + flag);
        if (status == null || status.length() < 23 || StringUtils.isEmpty(countStr)) {
            return "00";
        }
        DataStatistic dataStatistic = new DataStatistic();
        dataStatistic.setVersion(Integer.decode("0x" + status.substring(0, 2)) + "." + Integer.decode("0x" + status.substring(2, 4)));
        dataStatistic.setDeviceSn(StringUtils.reverseInWord(status.substring(4, 12)).toUpperCase());
        dataStatistic.setIrVoltage(Integer.decode("0x" + status.substring(14, 16)));
        dataStatistic.setCounterVoltage(Integer.decode("0x" + status.substring(18, 20)));
        dataStatistic.setDeleted(false);

        //===========================解析data数据==========================
        Integer count = Integer.valueOf(countStr, 16);
        List<String> lists = model.getData();
        if(CollectionUtils.isEmpty(lists) || !count.equals(lists.size())){
            logger.warn("~~接收到设备发来的数据不存在或个数不正确~~ data:" + lists + " count:" + count);
            return "00";
        }


        StringBuilder result = new StringBuilder();
        List<DataStatistic> dataStatistics = new ArrayList<>();
        boolean enable = true;
        for(String data : lists){
            if(checkCrc(data)){
                logger.warn("~~crc16 check failed~~ data:" + data);
                enable = false;
                break;
            }
            DataStatistic statistic = dataStatistic.copy();
            //上传时间
            dataStatistic.setDataTime(DateUtils.parseDate(StringUtils.parseHexStringToDecStringInWord(data.substring(0, 12)),
                    "yyMMddHHmmss"));
            //boolean focus = Integer.decode("0x" + data.substring(12, 14)) == 0;
            dataStatistic.setFocus(Integer.decode("0x" + data.substring(12, 14)) == 0);
            //进增量
            dataStatistic.setEntry(Integer.decode("0x" + StringUtils.reverseInWord(data.substring(14,22))));
            //出增量
            dataStatistic.setExit(Integer.decode("0x" + StringUtils.reverseInWord(data.substring(22,28))));
            dataStatistics.add(statistic);
        }
        if(!enable){
            logger.warn("~~上传数据失败~~");
            result.append("00").append(StringUtils.reverseInWord(flag)).append(StringUtils.format("", 24));
        } else{
            dataStatisticDao.addBatch(dataStatistics);

            DataStatistic latestStatistic = dataStatistics.get(dataStatistics.size() - 1);
            DeviceInfo deviceInfo = deviceInfoDao.findDeviceInfoBySn(latestStatistic.getDeviceSn());
            deviceInfo.setFocus(checkFocus(dataStatistics, 0.5f));
            deviceInfo.setLatestUpdateTime(new Date());
            deviceInfo.setCounterVoltage(latestStatistic.getCounterVoltage());
            deviceInfo.setIrVoltage(latestStatistic.getIrVoltage());
            deviceInfo.setLatestReceiveTime(latestStatistic.getDataTime());
            deviceInfoDao.save(deviceInfo);

            result.append("01"); //上传成功， 00表示失败
            result.append(StringUtils.reverseInWord(flag));
            LocalDateTime now = LocalDateTime.now();
            result.append(DateUtils.toHexString(now));
            result.append(StringUtils.format(Integer.toHexString(now.getDayOfWeek().getValue()), 2));
            result.append(DateUtils.toHexString(deviceInfo.getOpenTime()));
            result.append(DateUtils.toHexString(deviceInfo.getCloseTime()));
        }
        logger.info("~~~~ original ~~~~ :" + result.toString());
        String crcStr = Crc16Utils.getCRC16Str(result.toString().toUpperCase());
        logger.info("~~~~ crc ~~~~ " + crcStr);
        result.append(crcStr);
        logger.info("~~~~ return ~~~~ : " + result.toString().toUpperCase());
        return result.toString().toUpperCase();
    }

    private String getSetting(String data, String flag) {
        LocalDateTime now = LocalDateTime.now();
        logger.info("~~~ enter getsetting~~~ (" + now + ")");
        //如果crc校验不通过，则返回空字符串
        if (checkCrc(data)) {
            logger.warn("~~~ enter getsetting~~~ (CRC check failed)");
            return "";
        }
        DeviceInfo model = formatDataToReceiveModel(data);
        String sn = model.getSn();
        if(StringUtils.isEmpty(sn)){
            logger.warn("~~~ enter getsetting~~~ (sn getting failed)");
            return "";
        }
        logger.info("~~~ enter sn~~~ (" + sn + ")");

        DeviceInfo device = saveDeviceInfo(sn, model);
        if(device == null){
            logger.info("~~~ device " + sn + " is not existed");
            return "";
        }
        StringBuilder sb = new StringBuilder();
        //开始做校时, 只做到分钟
        //判断参数是否一致
        if (Objects.equals(Integer.decode("0x" + data.substring(80, 82)), now.getYear() - 2000) //year
                && Objects.equals(Integer.decode("0x" + data.substring(82, 84)), now.getMonthValue()) //month
                && Objects.equals(Integer.decode("0x" + data.substring(84, 86)), now.getDayOfMonth()) //day
                && Objects.equals(Integer.decode("0x" + data.substring(86, 88)), now.getHour()) //hour
                && Objects.equals(Integer.decode("0x" + data.substring(88, 90)), now.getMinute()) //minute
                //不对设备getsetting方法做时间校验，必须是03
//                && device.getCommondType().equals(model.getCommondType())
                && Objects.equals(model.getRecordingCycle(), device.getRecordingCycle())
                && Objects.equals(model.getUploadCycle(), device.getUploadCycle())
                && Objects.equals(model.getSpeed(), device.getSpeed())
                && Objects.equals(model.getOpenTime(), device.getOpenTime())
                && Objects.equals(model.getCloseTime(), device.getCloseTime())) {
            logger.info("所有校验通过");
            sb.append("05"); //参数确认
        } else{
            sb.append("04"); //参数新值
        }
        sb.append(StringUtils.reverseInWord(flag));
        sb.append(StringUtils.format("", 8));
        sb.append("03"); //command type
        sb.append(StringUtils.format(Integer.toHexString(device.getSpeed()), 2)); //speed
        sb.append(StringUtils.format(Integer.toHexString(device.getRecordingCycle()), 2));
        sb.append(StringUtils.format(Integer.toHexString(device.getUploadCycle()), 2));
        sb.append(StringUtils.format("", 18)); //fixed upload time uploadTime(1-4)
        sb.append(StringUtils.format(Integer.toHexString(device.getMode()), 2));//mode
        sb.append(StringUtils.format(Integer.toHexString(device.getDisplayType()), 2));
        sb.append(StringUtils.format("", 42)); //mac(1-3)
        sb.append(DateUtils.toHexString(LocalDateTime.now()));
        sb.append("00");
        sb.append(DateUtils.toHexString(device.getOpenTime()));
        sb.append(DateUtils.toHexString(device.getCloseTime()));
        sb.append("0000");
        logger.info("~~~~ original ~~~~ :" + sb.toString());
        String crcStr = Crc16Utils.getCRC16Str(sb.toString().toUpperCase());
        logger.info("~~~~ crc ~~~~ " + crcStr);
        sb.append(crcStr);
        logger.info("~~~~ return ~~~~ : " + sb.toString().toUpperCase());
        return sb.toString().toUpperCase();
    }

    private DeviceInfo saveDeviceInfo(String sn, DeviceInfo dataInfo){
        DeviceInfo device = deviceInfoDao.findDeviceInfoBySn(sn);
        if(device == null){
            return null;
        }
        //如果不等于空，那么更新一下上次接收时间
        device.setCommandType(dataInfo.getCommandType());
        device.setSpeed(device.getSpeed() == null ? 0 : device.getSpeed());
        device.setRecordingCycle(device.getRecordingCycle() == null ? 5 : device.getRecordingCycle());
        device.setUploadCycle(device.getUploadCycle() == null ? 5 : device.getUploadCycle());
        device.setFixedUploadTime(dataInfo.getFixedUploadTime());
        device.setUploadTime1(dataInfo.getUploadTime1());
        device.setUploadTime2(dataInfo.getUploadTime2());
        device.setUploadTime3(dataInfo.getUploadTime3());
        device.setUploadTime4(dataInfo.getUploadTime4());
        device.setMode(device.getMode() == null ? 0 : device.getMode());
        device.setDisplayType(device.getDisplayType() == null ? 2 : device.getDisplayType());
        device.setMac1(dataInfo.getMac1());
        device.setMac2(dataInfo.getMac2());
        device.setMac3(dataInfo.getMac3());
        device.setOpenTime(device.getOpenTime() == null ? OPEN_TIME : device.getOpenTime());
        device.setCloseTime(device.getCloseTime() == null ? CLOSE_TIME : device.getCloseTime());
        device.setLatestUpdateTime(new Date());
        deviceInfoDao.save(device);
        return device;
    }

    private DeviceInfo formatDataToReceiveModel(String data){
        DeviceInfo device = new DeviceInfo();
        // 设备串号sn
        device.setSn(StringUtils.reverseInWord(data.substring(0, 8)));
        device.setCommandType(Byte.decode("0x" + data.substring(8, 10)).intValue());
        device.setSpeed(Byte.decode("0x" + data.substring(10, 12)).intValue());
        device.setRecordingCycle(Byte.decode("0x" + data.substring(12, 14)).intValue());
        device.setUploadCycle(Byte.decode("0x" + data.substring(14, 16)).intValue());
        device.setFixedUploadTime(Byte.decode("0x" + data.substring(16, 18)).intValue());
        device.setUploadTime1(DateUtils.parseTime(StringUtils.parseHexStringToDecStringInWord(data.substring(18, 22)), "HHmm"));
        device.setUploadTime2(DateUtils.parseTime(StringUtils.parseHexStringToDecStringInWord(data.substring(22, 26)), "HHmm"));
        device.setUploadTime3(DateUtils.parseTime(StringUtils.parseHexStringToDecStringInWord(data.substring(26, 30)), "HHmm"));
        device.setUploadTime4(DateUtils.parseTime(StringUtils.parseHexStringToDecStringInWord(data.substring(30, 34)), "HHmm"));
        device.setMode(Byte.decode("0x" + data.substring(34, 36)).intValue());
        device.setDisplayType(Byte.decode("0x" + data.substring(36, 38)).intValue());
        device.setMac1(StringUtils.insertInWord(data.substring(38, 50), ":") + " " + data.substring(50, 52));
        device.setMac2(StringUtils.insertInWord(data.substring(52, 64), ":") + " " + data.substring(64, 66));
        device.setMac3(StringUtils.insertInWord(data.substring(66, 78), ":") + " " + data.substring(78, 80));
        device.setOpenTime(DateUtils.parseTime(StringUtils.parseHexStringToDecStringInWord(data.substring(94, 98)), "HHmm"));
        device.setCloseTime(DateUtils.parseTime(StringUtils.parseHexStringToDecStringInWord(data.substring(98, 102)), "HHmm"));
        return device;
    }

    //==========================校验设备是否失去焦点========================
    private boolean checkFocus(List<DataStatistic> dataStatistics, float threshold){
        if(threshold > 1f){
            threshold = 1f;
        } else if(threshold < 0f){
            threshold = 0f;
        }
        int count = 0;
        for(int i = dataStatistics.size() - 1; i >= 0; --i){
            if(dataStatistics.get(i).isFocus()){
                break;
            }
            ++count;
        }
        return count / ((float) dataStatistics.size()) <= threshold;
    }

    private boolean checkCrc(String s) {
        int len = s.length() - 4;
        return len < 4 ||
                !Integer.decode("0x" + s.substring(len)).equals(
                        Integer.decode("0x" + Crc16Utils.getCRC16Str(s.substring(0, len).toUpperCase())));
    }
}
