//package com.raysdata.riskdataanalyzeserver.service;
//
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class RiskInfosServiceTest {
//   /* @Autowired
//    private RiskInfosService riskInfosService;
//
//    @Test
//    public void getPlanPublishCount() {
//
//        Map resultmap = riskInfosService.planPublishCount(0);
//        resultmap.forEach((k, v) -> System.out.println("key:value = " + k + ":" + v));
//    }
//
//    @Test
//    public void stateGridRiskLevelCount() {
//        Map resultmap = riskInfosService.stateGridRiskLevelCount(4);
//        resultmap.forEach((k, v) -> System.out.println("key:value = " + k + ":" + v));
//    }
//
//    @Test
//    public void workRiskLevelCount() {
//        Map resultmap = riskInfosService.workRiskLevelCount(4);
//        resultmap.forEach((k, v) -> System.out.println("key:value = " + k + ":" + v));
//    }
//
//    @Test
//    public void warnRiskCount() {
//        Map resultmap = riskInfosService.warnRiskCount(0);
//        resultmap.forEach((k, v) -> System.out.println("key:value = " + k + ":" + v));
//    }
//
//    @Test
//    public void areaRiskCountInfo() {
//        List<Map> resultList = riskInfosService.areaRiskCountInfo();
//        resultList.stream().forEach(map -> {
//            map.forEach((k, v) -> System.out.println("key:value = " + k + ":" + v));
//        });
//    }
//
//    @Test
//    public void warnCount() {
//        List<Map> resultList = riskInfosService.warnCount(4,"2020-01-20 16:01:45","2020-02-20 16:01:45");
//        resultList.stream().forEach(map -> {
//            map.forEach((k, v) -> System.out.println("key:value = " + k + ":" + v));
//        });
//    }
//
//    @Test
//    public void voltageClassCount() {
//        List<Map> resultList = riskInfosService.voltageClassCount(4,"2020-01-20 16:01:45","2020-02-20 16:01:45");
//        resultList.stream().forEach(map -> {
//            map.forEach((k, v) -> System.out.println("key:value = " + k + ":" + v));
//        });
//    }
//
//    @Test
//    public void failureReasonCount() {
//        List<Map> resultList = riskInfosService.failureReasonCount(4,"2020-01-20 16:01:45","2020-02-20 16:01:45");
//        resultList.stream().forEach(map -> {
//            map.forEach((k, v) -> System.out.println("key:value = " + k + ":" + v));
//        });
//    }
//
//    @Test
//    public void riskDevCount() {
//        List<Map> resultList = riskInfosService.riskDevCount(4,"2020-01-20 16:01:45","2020-02-20 16:01:45");
//        resultList.stream().forEach(map -> {
//            map.forEach((k, v) -> System.out.println("key:value = " + k + ":" + v));
//        });
//    }
//
//    @Test
//    public void riskKeepDayCount() {
//        List<Map> resultList = riskInfosService.riskKeepDayCount(4,"2020-01-20 16:01:45","2020-02-20 16:01:45");
//        resultList.stream().forEach(map -> {
//            map.forEach((k, v) -> System.out.println("key:value = " + k + ":" + v));
//        });
//    }
//
//    @Test
//    public void warnDevTypeCount() {
//        List<Map> resultList = riskInfosService.warnDevTypeCount(5,"2020-01-20 16:01:45","2020-02-20 16:01:45");
//        resultList.stream().forEach(map -> {
//            map.forEach((k, v) -> System.out.println("key:value = " + k + ":" + v));
//        });
//    }
//
//    @Test
//    public void allRiskWarnInfosList() {
//        String jsonStr = "{\n" +
//                "\"pageSize\":\t\"1\",\n" +
//                "\"pageNo\":\"0\",\n" +
//                "\"dataStr\":\n" +
//                "{\"title\":\"冀北\"\n" +
//                "}\n" +
//                "}";
//        Map<String, Object> resultList = riskInfosService.allRiskWarnInfosList(jsonStr);
//        resultList.forEach((k, v) -> System.out.println("key:value = " + k + ":" + v));
//    }
//
//    @Test
//    public void gridWarnContent() {
//        String jsonStr = "124";
//        Map<String, Object> resultList = riskInfosService.gridWarnContent(jsonStr);
//        resultList.forEach((k, v) -> System.out.println("key:value = " + k + ":" + v));
//    }
//
//    @Test
//    public void getSrpRiskSysTab() {
//        Map<String,List<String>> resultList = riskInfosService.getSrpRiskSysTab();
//        *//*resultList.stream().forEach(map -> {
//            map.forEach((k, v) -> System.out.println("key:value = " + k + ":" + v));
//        });*//*
//    }*/
//
//
//}
