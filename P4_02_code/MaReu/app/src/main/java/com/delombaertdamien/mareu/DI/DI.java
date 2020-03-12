package com.delombaertdamien.mareu.DI;

import com.delombaertdamien.mareu.service.DummyMeetingApiService;
import com.delombaertdamien.mareu.service.MeetingApiService;

public class DI {

        private static MeetingApiService service = new DummyMeetingApiService();

        /**
         * Get an instance on @{@link MeetingApiService}
         * @return
         */
        public static MeetingApiService getMettingApiService() {
            return service;
        }

        /**
         * Get always a new instance on @{@link MeetingApiService}. Useful for tests, so we ensure the context is clean.
         * @return
         */
        public static MeetingApiService getNewInstanceApiService() {
            return new DummyMeetingApiService();
        }
    }

