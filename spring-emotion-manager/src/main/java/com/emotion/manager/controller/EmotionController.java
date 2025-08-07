package com.emotion.manager.controller;

import com.emotion.manager.entity.EmotionResult;
import com.emotion.manager.repository.EmotionResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;

@Controller
public class EmotionController {
    
    @Autowired
    private EmotionResultRepository emotionResultRepository;
    
    // 메인 대시보드 페이지
    @GetMapping("/")
    public String dashboard(Model model) {
        List<EmotionResult> recentResults = emotionResultRepository.findTop50ByOrderByAnalysisTimeDesc();
        List<Object[]> statistics = emotionResultRepository.getEmotionStatistics();
        
        model.addAttribute("recentResults", recentResults);
        model.addAttribute("statistics", statistics);
        model.addAttribute("totalCount", emotionResultRepository.count());
        
        return "dashboard";
    }
    
    // 모든 분석 결과 조회 페이지
    @GetMapping("/results")
    public String results(Model model, @RequestParam(required = false) String emotion) {
        List<EmotionResult> results;
        
        if (emotion != null && !emotion.isEmpty()) {
            results = emotionResultRepository.findByDominantEmotionOrderByAnalysisTimeDesc(emotion);
        } else {
            results = emotionResultRepository.findTop50ByOrderByAnalysisTimeDesc();
        }
        
        model.addAttribute("results", results);
        model.addAttribute("selectedEmotion", emotion);
        
        return "results";
    }
    
    // 상세 결과 조회 페이지
    @GetMapping("/result/{id}")
    public String resultDetail(@PathVariable Long id, Model model) {
        Optional<EmotionResult> result = emotionResultRepository.findById(id);
        
        if (result.isPresent()) {
            model.addAttribute("result", result.get());
            return "result-detail";
        } else {
            return "redirect:/results";
        }
    }
}

// REST API 컨트롤러
@RestController
class EmotionRestController {
    
    @Autowired
    private EmotionResultRepository emotionResultRepository;
    
    // REST API: 모든 결과 조회
    @GetMapping("/api/results")
    public List<EmotionResult> getAllResults() {
        return emotionResultRepository.findTop50ByOrderByAnalysisTimeDesc();
    }
    
    // REST API: 특정 결과 조회
    @GetMapping("/api/result/{id}")
    public EmotionResult getResult(@PathVariable Long id) {
        return emotionResultRepository.findById(id).orElse(null);
    }
    
    // REST API: 감정별 통계
    @GetMapping("/api/statistics")
    public List<Object[]> getStatistics() {
        return emotionResultRepository.getEmotionStatistics();
    }
}
