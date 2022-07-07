package project.trello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project.trello.service.ListService;

import java.util.List;

@RestController
public class ListController {

    private final ListService listService;

    @Autowired
    public ListController(ListService listService) {
        this.listService = listService;
    }


    @GetMapping("getlists")
    public List<project.trello.model.List> getLists(){
        return listService.getLists();
    }

    @PostMapping("createlist")
    public project.trello.model.List createBoard(@RequestBody project.trello.model.List list){
        return listService.createList(list);
    }

    @PutMapping("/edit-list/{list_id}")
    public project.trello.model.List editList(@PathVariable Long list_id,
                                              @RequestBody project.trello.model.List list){
        return listService.editList(list_id, list);
    }
}