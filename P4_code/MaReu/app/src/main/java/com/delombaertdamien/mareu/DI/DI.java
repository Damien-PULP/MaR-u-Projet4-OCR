package com.delombaertdamien.mareu.DI;

import com.delombaertdamien.mareu.service.DummyMeetingApiService;
import com.delombaertdamien.mareu.service.MeetingApiService;

public class DI {

        private static MeetingApiService service = new DummyMeetingApiService();

        public static MeetingApiService getMeetingApiService() {
            return service;
        }
        public static MeetingApiService getNewInstanceApiService() {
            return new DummyMeetingApiService();
        }
    }

