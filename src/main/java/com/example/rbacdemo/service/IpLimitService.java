package com.example.rbacdemo.service;

import com.example.rbacdemo.common.annotion.IpLimit;

public interface IpLimitService {
    boolean check(IpLimit ipLimit, String ip);
}
