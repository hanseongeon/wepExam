package com.example.ms1.Notebook;

import com.example.ms1.note.note.Note;
import com.example.ms1.note.note.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/book")
public class NotebookController {
    private final NotebookRepository notebookRepository;
    private final NoteService noteService;

    @PostMapping("/write")
    public String write() {
        Notebook notebook = new Notebook();
        notebook.setName("새노트북");
        notebookRepository.save(notebook);
        noteService.savedefalut(notebook);
        return "redirect:/";
    }

    @GetMapping("/{notebookId}")
    public String detail(@PathVariable("notebookId")Long notebookId){
        Notebook notebook = notebookRepository.findById(notebookId).orElseThrow();
        Note note = notebook.getNoteList().get(0);

        return String.format("redirect:/books/%s/notes/%s",notebookId,note.getId());
    }
}
