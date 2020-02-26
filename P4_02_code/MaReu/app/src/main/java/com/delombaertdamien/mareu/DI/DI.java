package com.delombaertdamien.mareu.DI;

import com.delombaertdamien.mareu.service.DummyMettingApiService;
import com.delombaertdamien.mareu.service.MettingApiService;

public class DI {

        private static MettingApiService service = new DummyMettingApiService();

        /**
         * Get an instance on @{@link MettingApiService}
         * @return
         */
        public static MettingApiService getMettingApiService() {
            return service;
        }

        /**
         * Get always a new instance on @{@link MettingApiService}. Useful for tests, so we ensure the context is clean.
         * @return
         */
        public static MettingApiService getNewInstanceApiService() {
            return new DummyMettingApiService();
        }
    }

