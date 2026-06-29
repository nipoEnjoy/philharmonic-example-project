package com.npopov.philharmonic.events.competition.dto;

import java.time.LocalDateTime;

public class CompetitionResultResponse {
    private Long id;
    private Long competitionId;
    private String competitionName;
    private Long artistId;
    private String artistStageName;
    private Long place;
    private String award;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CompetitionResultResponse(Long id, Long competitionId, String competitionName, Long artistId, String artistName, Long place, String award, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.competitionId = competitionId;
        this.competitionName = competitionName;
        this.artistId = artistId;
        this.artistStageName = artistName;
        this.place = place;
        this.award = award;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public CompetitionResultResponse() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(Long competitionId) {
        this.competitionId = competitionId;
    }

    public String getCompetitionName() {
        return competitionName;
    }

    public void setCompetitionName(String competitionName) {
        this.competitionName = competitionName;
    }

    public Long getArtistId() {
        return artistId;
    }

    public void setArtistId(Long artistId) {
        this.artistId = artistId;
    }

    public Long getPlace() {
        return place;
    }

    public void setPlace(Long place) {
        this.place = place;
    }

    public String getAward() {
        return award;
    }

    public void setAward(String award) {
        this.award = award;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getArtistStageName() {
        return artistStageName;
    }

    public void setArtistStageName(String artistStageName) {
        this.artistStageName = artistStageName;
    }
}