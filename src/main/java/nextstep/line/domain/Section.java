package nextstep.line.domain;

import nextstep.station.domain.Station;

import javax.persistence.*;

@Entity
public class Section implements Comparable<Section> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Station upStation;
    @ManyToOne(fetch = FetchType.LAZY)
    private Station downStation;
    private int distance;

    protected Section () {}

    private Section(Station upStation, Station downStation, int distance) {
        this.upStation = upStation;
        this.downStation = downStation;
        this.distance = distance;
    }

    public static Section create(Station upStation, Station downStation, int distance) {
        return new Section(upStation, downStation, distance);
    }

    public Long getId() {
        return id;
    }

    public Station getUpStation() {
        return upStation;
    }

    public Station getDownStation() {
        return downStation;
    }

    public int getDistance() {
        return distance;
    }

    @Override
    public int compareTo(Section section) {
        if (this.downStation.equals(section.getUpStation())) {
            return -1;
        }
        if (this.upStation.equals(section.getDownStation())) {
            return 1;
        }
        return 0;
    }
}