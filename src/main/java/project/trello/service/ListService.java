package project.trello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.trello.model.Archive;
import project.trello.model.Board;
import project.trello.model.Card;
import project.trello.repository.ArchiveRepository;
import project.trello.repository.BoardRepository;
import project.trello.repository.ListRepository;

import java.util.List;

@Service
public class ListService {

    private final ListRepository listRepository;
    private final BoardRepository boardRepository;
    private final ArchiveRepository archiveRepository;

    @Autowired
    public ListService(ListRepository listRepository, BoardRepository boardRepository, ArchiveRepository archiveRepository) {
        this.listRepository = listRepository;
        this.boardRepository = boardRepository;
        this.archiveRepository = archiveRepository;
    }

    public List<project.trello.model.List> getLists() {
        return listRepository.findAll();
    }

    public Board createList(Long board_id,project.trello.model.List list) {
        listRepository.save(list);
        Board board = boardRepository.findById(board_id).get();
        project.trello.model.List list1 = listRepository.findById(list.getId()).get();
        board.getLists().add(list1);
        return boardRepository.save(board);
    }

    public project.trello.model.List editList(Long list_id, project.trello.model.List list) {
        project.trello.model.List foundedList = listRepository.findById(list_id).get();
        foundedList.setTitle(list.getTitle());

        return listRepository.save(foundedList);
    }

    public void deleteList(Long list_id) {
        boolean exists = listRepository.existsById(list_id);
        if (!exists) {
            throw new IllegalStateException("list with id " + list_id +
                    " does not exist!");
        }
        if(archiveRepository.findAll().isEmpty()){
            throw new IllegalStateException("archive is empty !");
        }
        Archive archive = archiveRepository.findById(1L).get();
        List<project.trello.model.List> archiveLists = archive.getLists();
        for(project.trello.model.List list : archiveLists){
            if(list.getId().equals(list_id)){
                listRepository.deleteById(list_id);
                return;
            }
        }
        throw new IllegalStateException("you should archive this list first !");
    }

}