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

        private List<Integer> currentNode;
        private List<Integer> previousNode;
        private String startMeetingDay;
        private String endMeetingDay;
        private String startRestDay;
        private String endRestDay;
        private boolean isMeetingAvailableForDay;
        private boolean isThisFirstMeetingOfWeek;
        private boolean isThisLastMeetingOfWeek;
        private boolean isFirstMeetingOfTheDay;
        private boolean isLastMeetingOfTheDay;

        private int maxRestTime, currentInterval;
        private final int MAX_MINUTE_PER_DAY = 1440;

        public RestTimeCalculator(List<Integer> currentNode, List<Integer> previousNode, String startMeetingDay,
                                  String endMeetingDay, String startRestDay, String endRestDay, boolean isMeetingAvailableForDay, boolean isThisFirstMeetingOfWeek,
                                  boolean isThisLastMeetingOfWeek, boolean isFirstMeetingOfTheDay, boolean isLastMeetingOfTheDay) {
            this.currentNode = currentNode;
            this.previousNode = previousNode;
            this.startMeetingDay = startMeetingDay;
            this.endMeetingDay = endMeetingDay;
            this.isMeetingAvailableForDay = isMeetingAvailableForDay;
            this.isThisFirstMeetingOfWeek = isThisFirstMeetingOfWeek;
            this.isThisLastMeetingOfWeek = isThisLastMeetingOfWeek;
            this.isFirstMeetingOfTheDay = isFirstMeetingOfTheDay;
            this.isLastMeetingOfTheDay = isLastMeetingOfTheDay;
            this.startRestDay = startRestDay;
            this.endRestDay = endRestDay;
        }

        public void calculateRestTime() {
            if (isMeetingAvailableForDay) {
                if (isThisFirstMeetingOfWeek && Objects.isNull(previousNode)) {
                    currentInterval = currentNode.get(0);
                    updateMaxRestTime(currentInterval, startRestDay, endRestDay);
                    previousNode = currentNode;
                } else if (isThisLastMeetingOfWeek) {
                    currentInterval = getRemainingMinuteAfterLastNode(currentNode.get(1), endMeetingDay);
                    updateMaxRestTime(currentInterval, startRestDay, endRestDay);
                } else if (isFirstMeetingOfTheDay) {
                    currentInterval = currentNode.get(0);
                    updateMaxRestTime(currentInterval, startRestDay, endRestDay);
                } else if (isLastMeetingOfTheDay) {
                    currentInterval = calculateRemainingTime(currentNode.get(1), 0);
                    updateMaxRestTime(currentInterval, startRestDay, endRestDay);
                } else {
                    currentInterval = currentNode.get(0) - previousNode.get(1);
                    updateMaxRestTime(currentInterval, startRestDay, endRestDay);
                }
            } else {
                updateMaxRestTime(MAX_MINUTE_PER_DAY, startRestDay, endRestDay);
            }

        }

        private void printRestTime(){
            System.out.println("James can sleep "+maxRestTime+" minutes from "+startRestDay+" to "+endRestDay);
        }

        private void updateMaxRestTime(int currentInterval, String startRestDayName, String endRestDayName) {
            maxRestTime = maxRestTime + currentInterval;
            this.startRestDay=startRestDayName;
            this.endRestDay=endRestDayName;

        }


        private int getRemainingMinuteAfterLastNode(int endTimeInMinute, String day) {
            int remainingTime = 0;
            switch (day) {
                case "Sun": {
                    remainingTime = calculateRemainingTime(endTimeInMinute, 6);
                    break;
                }
                case "Mon": {
                    remainingTime = calculateRemainingTime(endTimeInMinute, 5);
                    break;
                }
                case "Tue": {
                    remainingTime = calculateRemainingTime(endTimeInMinute, 4);
                    break;
                }
                case "Wed": {
                    remainingTime = calculateRemainingTime(endTimeInMinute, 3);
                    break;
                }
                case "Thu": {
                    remainingTime = calculateRemainingTime(endTimeInMinute, 2);
                    break;
                }
                case "Fri": {
                    remainingTime = calculateRemainingTime(endTimeInMinute, 1);
                    break;
                }
                case "Sat": {
                    remainingTime = calculateRemainingTime(endTimeInMinute, 0);
                    break;
                }
                default: {
                    System.out.println("Unexpected Case");
                }
            }

            return remainingTime;
        }

        private int calculateRemainingTime(int endTime, int remainingDays){
            int remainingMinOfTheDay = MAX_MINUTE_PER_DAY - endTime;
            return remainingMinOfTheDay + MAX_MINUTE_PER_DAY * remainingDays;
        }

    }

}
