package com.kib.weblab4.math;

import org.springframework.stereotype.Service;

@Service
public class ResultCalculator {

    public boolean calculate(int x, float y, int r) {
        return checkFirstQuarter(x, y, r) ||
                checkSecondQuarter(x, y, r) ||
                checkThirdQuarter(x, y, r);
    }

    public boolean checkFirstQuarter(int x, float y, int r) {
        return x >= 0 && y >= 0 && x * x + y * y < r * r;
    }

    public boolean checkSecondQuarter(int x, float y, int r) {
        return x <= 0 && x >= r && y >= 0 && y <= r / 2f;
    }

    public boolean checkThirdQuarter(int x, float y, int r) {
        return x <= 0 && y <= 0 && y >= -x - r / 2f;
    }
}
