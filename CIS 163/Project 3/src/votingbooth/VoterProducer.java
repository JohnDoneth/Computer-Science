package votingbooth;

import java.util.ArrayList;

/**
 * @author Roger Ferguson
 */
public class VoterProducer implements ClockListener {

    private int nextPerson = 0;
    private Booth[] booths;
    private int numOfTicksNextPerson;
    private int toleranceTime;
    private int totalPeople;

    private ArrayList<CheckInTable> tables;
    private ArrayList<Voter> normalVoters;
    private ArrayList<Voter> limitedVoters;
    private ArrayList<Voter> specialVoters;

    private Integer maxNormalVoters;
    private Integer maxLimitedVoters;
    private Integer maxSpecialVoters;

    private int count;

    public VoterProducer(Booth[] booths,
                         int numOfTicksNextPerson,
                         int toleranceTime,
                         Integer totalPeople) {

        this.booths = booths;
        this.numOfTicksNextPerson = numOfTicksNextPerson;
        this.toleranceTime = toleranceTime;
        this.totalPeople = totalPeople;
        this.tables = new ArrayList<>();
        this.normalVoters = new ArrayList<>();
        this.limitedVoters = new ArrayList<>();
        this.specialVoters = new ArrayList<>();
        this.maxNormalVoters = ((Double)(totalPeople.doubleValue() * 0.7)).intValue();
        this.maxLimitedVoters = ((Double)(totalPeople.doubleValue() * 0.2)).intValue();
        this.maxSpecialVoters = ((Double)(totalPeople.doubleValue() * 0.1)).intValue();
        this.count = 0;
    }

    public void event(int tick) {
        /** This dictates when to generate a new voter */
        if (nextPerson <= tick) {
            nextPerson = tick + numOfTicksNextPerson;

            /** Determines what type of voter to generate */

            Voter person;

            if (limitedVoters.size() < maxLimitedVoters) {
                person = new LimitedTimeVoter();
                limitedVoters.add(person);
            } else if (specialVoters.size() < maxSpecialVoters) {
                person = new SpecialNeedsVoter();
                specialVoters.add(person);
            } else {
                person = new Voter();
                normalVoters.add(person);
            }

            count++;

            person.setID(count);
            person.setTolerance(toleranceTime);

            // Here send the voter to a checkIn A-L or M-Z
            for (int i = 0; i < tables.size(); i++) {
                if (i < tables.size() - 1) {
                    if (tables.get(i).getVoterQ() <= tables.get(i+1).getVoterQ()) {
                        tables.get(i).addVoter(person);
                        break;
                    }
                } else {
                    if (tables.get(i).getVoterQ() <= tables.get(0).getVoterQ()) {
                        tables.get(i).addVoter(person);
                        break;
                    }
                }
            }
            person.setStatus(VoterStatus.CHECKING_IN);
        }
    }

    public void addTable(CheckInTable table) {
        tables.add(table);
    }

    public int getAllThrougPut() {
        int completed = 0;
        for (Booth b : booths) {
            completed += b.getThroughPut();
        }
        return completed;
    }

    public ArrayList<Voter> getNormalVoters() {
        return normalVoters;
    }

    public ArrayList<Voter> getLimitedVoters() {
        return limitedVoters;
    }

    public ArrayList<Voter> getSpecialVoters() {
        return specialVoters;
    }

}
