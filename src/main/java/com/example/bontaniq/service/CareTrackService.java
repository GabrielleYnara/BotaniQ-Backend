package com.example.bontaniq.service;

import com.example.bontaniq.model.CareTrack;
import com.example.bontaniq.repository.CareTrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CareTrackService extends ServiceSharedResources{
    private final CareTrackRepository careTrackRepository;

    @Autowired
    public CareTrackService(CareTrackRepository careTrackRepository) {
        this.careTrackRepository = careTrackRepository;
    }

}
