package com.epam.jmp.marathon;

public enum RunnerState {

    RUNNING {
        @Override
        public String toString() {
            return ".";
        }
    },
    SLEEPING {
        @Override
        public String toString() {
            return "o";
        }
    },
    WAITING {
        @Override
        public String toString() {
            return "*";
        }
    };
}