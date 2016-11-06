package server.servlets.users_data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by Ольга on 06.11.2016.
 */
public class Leaderboard {
    private int id;
    private int user_id;
    private int score;

    private static final ObjectMapper mapper = new ObjectMapper();


    public static Leaderboard readJson(String json) throws IOException {
        return mapper.readValue(json, Leaderboard.class);
    }

    public String writeJson() throws JsonProcessingException {
        return mapper.writeValueAsString(this);
    }


    public Leaderboard() {
    }
    public int getId() {
        return id;
    }

    public Leaderboard setId(int id) {
        this.id = id;
        return this;
    }
    public int getUser() {
        return this.user_id;
    }

    public Leaderboard setUser(int user_id) {
        this.user_id = user_id;
        return this;
    }

    public int getScore() {
        return score;
    }

    public Leaderboard setScore(int score) {
        this.score = score;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Leaderboard person = (Leaderboard) o;

        if (id != person.id) return false;
        if (score != person.score) return false;
        return user_id != 0 ? (user_id == person.user_id) : person.user_id == 0;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + score;
        result = 31 * result + (user_id == 0 ? user_id : 0);
        return result;
    }
}
