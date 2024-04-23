package com.example.ms1.note.note;

import com.example.ms1.Notebook.Notebook;
import com.example.ms1.Notebook.NotebookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/books/{notebookId}/notes")
public class NoteController {

    private final NoteRepository noteRepository;
    private final NoteService noteService;
    private final NotebookRepository notebookRepository;

    @RequestMapping("/test")
    @ResponseBody
    public String test() {
        return "test";
    }

    @PostMapping("/write")
    public String write(@PathVariable("notebookId") Long notebookId) {
        Notebook notebook = notebookRepository.findById(notebookId).orElseThrow();
        noteService.savedefalut(notebook);

        return  String.format("redirect:/books/%s/notes/%s",notebookId,notebook.getNoteList().get(0).getId());
    }

    @GetMapping("/{id}")
    public String detail(Model model, @PathVariable("notebookId") Long notebookId,@PathVariable("id") Long id) {
        List<Notebook> notebookList = notebookRepository.findAll();
        Notebook targetNotebook = notebookRepository.findById(notebookId).orElseThrow();
        List<Note> noteList = noteRepository.findByNotebook(targetNotebook);
        Note note = noteRepository.findById(id).get();
        model.addAttribute("targetNote", note);
        model.addAttribute("noteList", noteList);
        model.addAttribute("notebookList",notebookList);
        model.addAttribute("targetNotebook",targetNotebook);


        return "main";
    }
    @PostMapping("/{id}/update")
    public String update(@PathVariable("notebookId") Long notebookId,@PathVariable("id") Long id, String title, String content) {
        if(title.trim().length() == 0){
            title = "제목 없음";
        }
        Note note = noteRepository.findById(id).get();
        note.setTitle(title);
        note.setContent(content);

        noteRepository.save(note);
        return String.format("redirect:/books/%s/notes/%s",notebookId,id);
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("notebookId") Long notebookId,@PathVariable("id") Long id){
        noteRepository.deleteById(id);
        return "redirect:/";
    }
}
