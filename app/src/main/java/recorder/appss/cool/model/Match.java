package recorder.appss.cool.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Match extends GenericModel {

    @SerializedName("awayGoals")
    @Expose
    private Integer awayGoals;
    @SerializedName("currentStateStart")
    @Expose
    private double currentStateStart;
    @SerializedName("nextState")
    @Expose
    private Object nextState;
    @SerializedName("start")
    @Expose
    private double start;
    @SerializedName("goToExtraTime")
    @Expose
    private Boolean goToExtraTime;
    @SerializedName("season")
    @Expose
    private Season season;
    @SerializedName("homeTeam")
    @Expose
    private HomeTeam homeTeam;
    @SerializedName("aggregateScore")
    @Expose
    private Object aggregateScore;
    @SerializedName("venue")
    @Expose
    private Venue venue;
    @SerializedName("competition")
    @Expose
    private Competition competition;
    @SerializedName("limitedCoverage")
    @Expose
    private Boolean limitedCoverage;
    @SerializedName("dismissals")
    @Expose
    private Dismissals dismissals;
    @SerializedName("awayTeam")
    @Expose
    private AwayTeam awayTeam;
    @SerializedName("homeGoals")
    @Expose
    private Integer homeGoals;
    @SerializedName("penaltyShootout")
    @Expose
    private PenaltyShootout penaltyShootout;
    @SerializedName("extraTimeHasHappened")
    @Expose
    private Boolean extraTimeHasHappened;
    @SerializedName("isResult")
    @Expose
    private Boolean isResult;
    @SerializedName("outcome")
    @Expose
    private Outcome outcome;
    @SerializedName("round")
    @Expose
    private Round round;
    @SerializedName("currentState")
    @Expose
    private Integer currentState;

    public Integer getAwayGoals() {
        return awayGoals;
    }

    public void setAwayGoals(Integer awayGoals) {
        this.awayGoals = awayGoals;
    }

    public double getCurrentStateStart() {
        return currentStateStart;
    }

    public void setCurrentStateStart(double currentStateStart) {
        this.currentStateStart = currentStateStart;
    }

    public Object getNextState() {
        return nextState;
    }

    public void setNextState(Object nextState) {
        this.nextState = nextState;
    }

    public double getStart() {
        return start;
    }

    public void setStart(double start) {
        this.start = start;
    }

    public Boolean getGoToExtraTime() {
        return goToExtraTime;
    }

    public void setGoToExtraTime(Boolean goToExtraTime) {
        this.goToExtraTime = goToExtraTime;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public HomeTeam getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(HomeTeam homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Object getAggregateScore() {
        return aggregateScore;
    }

    public void setAggregateScore(Object aggregateScore) {
        this.aggregateScore = aggregateScore;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    public Boolean getLimitedCoverage() {
        return limitedCoverage;
    }

    public void setLimitedCoverage(Boolean limitedCoverage) {
        this.limitedCoverage = limitedCoverage;
    }

    public Dismissals getDismissals() {
        return dismissals;
    }

    public void setDismissals(Dismissals dismissals) {
        this.dismissals = dismissals;
    }

    public AwayTeam getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(AwayTeam awayTeam) {
        this.awayTeam = awayTeam;
    }

    public Integer getHomeGoals() {
        return homeGoals;
    }

    public void setHomeGoals(Integer homeGoals) {
        this.homeGoals = homeGoals;
    }

    public PenaltyShootout getPenaltyShootout() {
        return penaltyShootout;
    }

    public void setPenaltyShootout(PenaltyShootout penaltyShootout) {
        this.penaltyShootout = penaltyShootout;
    }

    public Boolean getExtraTimeHasHappened() {
        return extraTimeHasHappened;
    }

    public void setExtraTimeHasHappened(Boolean extraTimeHasHappened) {
        this.extraTimeHasHappened = extraTimeHasHappened;
    }

    public Boolean getIsResult() {
        return isResult;
    }

    public void setIsResult(Boolean isResult) {
        this.isResult = isResult;
    }

    public Outcome getOutcome() {
        return outcome;
    }

    public void setOutcome(Outcome outcome) {
        this.outcome = outcome;
    }

    public Round getRound() {
        return round;
    }

    public void setRound(Round round) {
        this.round = round;
    }

    public Integer getCurrentState() {
        return currentState;
    }

    public void setCurrentState(Integer currentState) {
        this.currentState = currentState;
    }

}
