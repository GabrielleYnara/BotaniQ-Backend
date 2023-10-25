package com.example.bontaniq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CareTrackerService extends ServiceSharedResources{
    private final CareTrackerService careTrackerService;

    @Autowired
    public CareTrackerService(CareTrackerService careTrackerService) {
        this.careTrackerService = careTrackerService;
    }
}
