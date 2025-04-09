package com.ambientese.grupo5.View;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;


@Controller
public class Pages {

    @GetMapping("/login")
    public String pageLogin(Model model, HttpServletRequest request) throws InterruptedException {
        String meuHeaderValue = request.getHeader("X-Requested-With");
        if (meuHeaderValue != null && meuHeaderValue.equals("InsideApplication")) {
            return "login";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/empresas")
    public String pageEmpresas(Model model, HttpServletRequest request) throws InterruptedException {
        String meuHeaderValue = request.getHeader("X-Requested-With");
        if (meuHeaderValue != null && meuHeaderValue.equals("InsideApplication")) {
            return "pages/cadastro-empresas";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/funcionarios")
    public String pageFuncionarios(Model model, HttpServletRequest request) throws InterruptedException {
        String meuHeaderValue = request.getHeader("X-Requested-With");
        if (meuHeaderValue != null && meuHeaderValue.equals("InsideApplication")) {
            return "pages/cadastro-funcionarios";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/ranking")
    public String pageRanking(Model model, HttpServletRequest request) throws InterruptedException {
        String meuHeaderValue = request.getHeader("X-Requested-With");
        if (meuHeaderValue != null && meuHeaderValue.equals("InsideApplication")) {
            return "pages/ranking";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/start-avaliacao")
    public String pageStartAvaliacao(Model model, HttpServletRequest request) throws InterruptedException {
        String meuHeaderValue = request.getHeader("X-Requested-With");
        if (meuHeaderValue != null && meuHeaderValue.equals("InsideApplication")) {
            return "pages/avaliacao/selecao-empresa";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/avaliacao")
    public String pageAvaliacao(Model model, HttpServletRequest request) throws InterruptedException {
        String meuHeaderValue = request.getHeader("X-Requested-With");
        if (meuHeaderValue != null && meuHeaderValue.equals("InsideApplication")) {
            return "pages/avaliacao/avaliacao";
        } else {
            return "redirect:/";
        }
    }
    
    @GetMapping("/result-avaliacao")
    public String pageResultAvaliacao(Model model, HttpServletRequest request) throws InterruptedException {
        String meuHeaderValue = request.getHeader("X-Requested-With");
        if (meuHeaderValue != null && meuHeaderValue.equals("InsideApplication")) {
            return "pages/avaliacao/result-avaliacao";
        } else {
            return "redirect:/";
        }
    }
}
