package com.sainath.graphqldemo.service;

import com.sainath.graphqldemo.model.Player;
import com.sainath.graphqldemo.model.Team;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class PlayerService {

    private final List<Player> players = new ArrayList<>();
    AtomicInteger id = new AtomicInteger(0);

    public List<Player> findAll() {
        return players;
    }

    public Optional<Player> findOne(Integer id) {
        return players.stream()
                .filter(player -> id.equals(player.id()))
                .findFirst();
    }

    public Player create(String name, Team team) {
        Player player = new Player(id.incrementAndGet(), name, team);
        players.add(player);

        return player;
    }

    public Player delete(Integer id) {
        Player deletedPlayer = players.stream()
                .filter(player -> id.equals(player.id()))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Player with id: " + id + " does not exists!"));

        players.remove(deletedPlayer);

        return deletedPlayer;
    }

    public Player update(Integer id, String name, Team team) {
        Player updatedPlayer = new Player(id, name, team);
        Optional<Player> optionalPlayer = players.stream()
                .filter(player -> id.equals(player.id()))
                .findFirst();

        if (optionalPlayer.isPresent()) {
            Player player = optionalPlayer.get();
            int index = players.indexOf(player);
            players.set(index, updatedPlayer);
        } else {
            throw new IllegalArgumentException("Player with id: " + id + " does not exists!");
        }

        return updatedPlayer;
    }

    @PostConstruct
    private void init() {
        players.add(new Player(id.incrementAndGet(), "Rohit Sharma", Team.MI));
        players.add(new Player(id.incrementAndGet(), "MS Dhoni", Team.CSK));
        players.add(new Player(id.incrementAndGet(), "Jasprit Bumrah", Team.MI));
        players.add(new Player(id.incrementAndGet(), "Ravindra Jadeja", Team.CSK));
        players.add(new Player(id.incrementAndGet(), "Virat Kohli", Team.RCB));
    }
}
