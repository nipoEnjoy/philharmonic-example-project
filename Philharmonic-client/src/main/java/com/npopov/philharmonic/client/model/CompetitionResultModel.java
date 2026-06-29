package com.npopov.philharmonic.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CompetitionResultModel {
    private Long id;
    private Long competitionId;
    private String competitionName;
    private Long artistId;
    private String artistStageName;
    private Integer place;
    private String award;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CompetitionResultModel() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getCompetitionId() { return competitionId; }
    public void setCompetitionId(Long competitionId) { this.competitionId = competitionId; }
    public String getCompetitionName() { return competitionName; }
    public void setCompetitionName(String competitionName) { this.competitionName = competitionName; }
    public Long getArtistId() { return artistId; }
    public void setArtistId(Long artistId) { this.artistId = artistId; }
    public String getArtistStageName() { return artistStageName; }
    public void setArtistStageName(String artistStageName) { this.artistStageName = artistStageName; }
    public Integer getPlace() { return place; }
    public void setPlace(Integer place) { this.place = place; }
    public String getAward() { return award; }
    public void setAward(String award) { this.award = award; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
