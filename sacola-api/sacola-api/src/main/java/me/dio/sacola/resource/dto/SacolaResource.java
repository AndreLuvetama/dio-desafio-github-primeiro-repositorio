package me.dio.sacola.resource.dto;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import me.dio.sacola.model.Item;
import me.dio.sacola.model.Sacola;
import me.dio.sacola.service.SacolaService;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@Api(value = "/ifood-devweek/sacolas")
@RestController
@RequestMapping("/ifood-devweek/sacolas")
@RequiredArgsConstructor
public class SacolaResource {
    private final SacolaService sacolaService;
    @PostMapping
    public Item incluirItemSacola(@RequestBody ItemDto itemDto){
        return sacolaService.incluirItemSacola(itemDto);
    }

    @GetMapping("/{id}")
    public Sacola verSacola(@PathVariable("id") Long id){
        return sacolaService.verSacola(id);
    }

    @PatchMapping("/fecharSacola/{sacolaId}")
    public Sacola fecharSacola(@PathVariable("sacolaId") Long sacolaId, @RequestParam("formaPagamanto") int formaPagamanto){
        return sacolaService.fecharSacola(sacolaId, formaPagamanto);
    }
}
