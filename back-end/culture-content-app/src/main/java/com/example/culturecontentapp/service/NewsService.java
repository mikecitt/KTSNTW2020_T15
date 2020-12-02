package com.example.culturecontentapp.service;

import com.example.culturecontentapp.exception.NewsNotFoundException;
import com.example.culturecontentapp.model.News;
import com.example.culturecontentapp.payload.request.NewsRequest;
import com.example.culturecontentapp.repository.NewsRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsService {

  private final NewsRepository repository;
  private ModelMapper mapper = new ModelMapper();

  @Autowired
  public NewsService(NewsRepository repository) {
    this.repository = repository;
  }

  public void addNews(NewsRequest newsRequest){
    News news = mapper.map(newsRequest, News.class);
    repository.save(news);
  }

  public void deleteNews(Long id) {
    News news = repository.findById(id).orElseThrow(()-> new NewsNotFoundException("The news you want to delete doesn't exist."));
    repository.delete(news);

  }
}
