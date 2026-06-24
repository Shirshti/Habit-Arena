package com.shrii.habitarena.Controller;

import com.shrii.habitarena.DTO.LeaderBoardDTO;
import com.shrii.habitarena.Service.LeaderBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class LeaderBoardController {

    @Autowired
    private LeaderBoardService leaderBoardService;

    @GetMapping("/leaderboard")
    private List<LeaderBoardDTO> getLeaderBoard(){
        return leaderBoardService.getLeaderBoard();
    }
}
