package com.example.ms1;

import com.example.ms1.Notebook.Notebook;
import com.example.ms1.Notebook.NotebookRepository;
import com.example.ms1.note.note.Note;
import com.example.ms1.note.note.NoteRepository;
import com.example.ms1.note.note.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final NotebookRepository notebookRepository;
    private final NoteService noteService;
    private final NoteRepository noteRepository;

    @RequestMapping("/")
    public String main(Model model) {
        List<Notebook> notebookList = notebookRepository.findAll();
        if(notebookList.isEmpty()){
            Notebook notebook = new Notebook();
            notebook.setName("새노트북");
            notebookRepository.save(notebook);
            noteService.savedefalut(notebook);
        }
        Notebook targetNotebook = notebookList.get(0);
        //1. DB에서 데이터 꺼내오기
        List<Note> noteList = noteRepository.findByNotebook(targetNotebook);

        //2. 꺼내온 데이터를 템플릿으로 보내기
        model.addAttribute("noteList", noteList);
        model.addAttribute("targetNote", noteList.get(0));
        model.addAttribute("notebookList",notebookList);
        model.addAttribute("targetNotebook",targetNotebook);

        return "main";
    }
}
