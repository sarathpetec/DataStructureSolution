package com.sarath.sample.demo;

import java.util.*;

public class Meeting {
    private static Map<String, List> meetingSchedule;
    private static final String[] WEEK = new String[]{"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
    private static List<List<Integer>> data = new ArrayList<>();
    private static int maxRestTime = 0, currentInterval = 0, maxMinutePerDay = 60 * 24;
    private static String restStartDay, restEndDay;


    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        int noOfMeeting = scan.nextInt() * 2;
        meetingSchedule = new HashMap<>(noOfMeeting);
        scan = new Scanner(System.in);
        System.out.println();
        String key = null;
        while (noOfMeeting-- > 0) {
            String meetingDetail = scan.next();
            //System.out.println(noOfMeeting + " ,meetingDetail :" + meetingDetail);
            key = updateMeetingSchedule(meetingDetail, noOfMeeting, key);
        }
        System.out.println(meetingSchedule.size());
        meetingSchedule.entrySet().iterator().forEachRemaining(stringListEntry -> {
            System.out.println("Key :" + stringListEntry.getKey());
            //System.out.println(stringListEntry.getValue());
            stringListEntry.getValue().stream().forEach(ints -> System.out.println("Value :" + ints));
            setData(stringListEntry.getValue());
            sortMyDailyMeeting();
        });
    }

    public static String updateMeetingSchedule(String meetingDetails, int noOfMeetingCount, String key) {
        if (noOfMeetingCount % 2 == 0) {
            //System.out.println("If :" + meetingDetails + ", Key :" + key + ", Contains Key :" + (meetingSchedule.containsKey(key)));
            List lstMeetingSchedule = meetingSchedule.containsKey(key) ? meetingSchedule.get(key) : new ArrayList();
            List time = convertStringMeetingDetailToIntArray(meetingDetails);
            lstMeetingSchedule.add(time);
            meetingSchedule.put(key, lstMeetingSchedule);
        } else {
            //System.out.println("Else :" + meetingDetails + ", Key :" + key + ", Contains Key :" + (meetingSchedule.containsKey(meetingDetails)));
            if (!meetingSchedule.containsKey(meetingDetails)) {
                List arr = new ArrayList();
                meetingSchedule.put(meetingDetails, arr);
            }
            return meetingDetails;
        }
        return null;
    }

    private static List convertStringMeetingDetailToIntArray(String meetingDetails) {
        List lst = new ArrayList();
        String meetingDetailsArray[] = meetingDetails.split("-");

        for (int i = 0; i < 2; i++) {
            String[] meetingTime = meetingDetailsArray[i].split(":");
            int minute = (Integer.parseInt(meetingTime[0]) * 60) + Integer.parseInt(meetingTime[1]);
            lst.add(minute);
        }
        //System.out.println("lst :"+lst);
        return lst;
    }

    private static void sortMyDailyMeeting() {
        Collections.sort(getData(), new Comparator<>() {
            public int compare(List<Integer> o1, List<Integer> o2) {
                if (o1 != null && o2 != null) {
                    Integer var1 = o1.get(0);
                    Integer var2 = o2.get(0);
                    return var1.compareTo(var2);
                }
                return 0;
            }
        });
        //getData().stream().forEach(list -> System.out.println("List :"+list));
    }

    public static List<List<Integer>> getData() {
        return data;
    }

    public static void setData(List<List<Integer>> data) {
        Meeting.data = data;
    }

    class RestTimeCalculator {

        private List<Integer> startTime;
        private List<Integer> endTime;
        private String startMeetingDay;
        private String endMeetingDay;
        private boolean isMeetingAvailable;

        private int maxRestTime;

        public RestTimeCalculator(List<Integer> startTime, List<Integer> endTime, String startMeetingDay,
                                  String endMeetingDay, boolean isMeetingAvailable) {
            this.startTime = startTime;
            this.endTime = endTime;
            this.startMeetingDay = startMeetingDay;
            this.endMeetingDay = endMeetingDay;
            this.isMeetingAvailable = isMeetingAvailable;
        }

        public void calculateRestTime() {

        }

    }

}
