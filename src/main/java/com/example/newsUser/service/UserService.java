package com.example.newsUser.service;


import com.example.newsUser.contract.CreateUserRequest;
import com.example.newsUser.contract.CreateUserResponse;
import com.example.newsUser.contract.EmailRequest;
import com.example.newsUser.contract.newsArticleWrapper.Article;
import com.example.newsUser.contract.newsArticleWrapper.newsArticle;
import com.example.newsUser.contract.sourceWrapper.ListSource;
import com.example.newsUser.contract.sourceWrapper.Source;
import com.example.newsUser.exception.UserNotFoundException;
import com.example.newsUser.model.entity.Call;
import com.example.newsUser.model.entity.PrefSources;
import com.example.newsUser.model.entity.Subscription;
import com.example.newsUser.model.entity.User;
import com.example.newsUser.model.repository.CRepository;
import com.example.newsUser.model.repository.SourceRepository;
import com.example.newsUser.model.repository.SubscriptionRespository;
import com.example.newsUser.model.repository.URepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;


@Service
public class UserService {
    @Autowired
    private URepository uRepository;

    @Autowired
    private SourceRepository sourceRepository;

    @Autowired
    private SubscriptionRespository subscriptionRespository;
    @Autowired
    private CRepository cRepository;
    @Autowired
    private EmailService emailService;

    public UserService() {
    }


    public CreateUserResponse createUser(CreateUserRequest createUserRequest) {
        User user  = new User(createUserRequest.getEmail(), createUserRequest.getSelected_country(), createUserRequest.getSelected_category(), null);

        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        if(!Pattern.matches(regexPattern, user.getEmail())){
            return null;
        }



        List<String> countries = Arrays.asList("ae","ar","at","au","be","bg","br","ca","ch","cn","co","cu","cz","de","eg","fr","gb","gr","hk","hu","id","ie","il","in","it","jp","kr","lt","lv","ma","mx","my","ng","nl","no","nz","ph","pl","pt","ro","rs","ru","sa","se","sg","si","sk","th","tr","tw","ua","us","ve","za");
        List<String> categories = Arrays.asList("business","entertainment","general","health","science","sports","technology");

        if(!countries.contains(user.getSelected_country())){
            return null;
        }
        if(!categories.contains(user.getSelected_category())){
            return null;
        }

        uRepository.save(user);
        EmailRequest emailRequest = new EmailRequest(user.getEmail(), "Welcome to NewsApp", "Thank you for signing up to NewsApp");
        emailService.sendEmail(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getText());

        CreateUserResponse createUserResponse = new CreateUserResponse(user.getUniqueID());
        return createUserResponse;

    }


    public List<Article> getNews(Long uid){
        String url = "https://newsapi.org/v2/top-headlines/?country=";

        Optional<User> userOptional = uRepository.findById(uid);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String country = user.getSelected_country();
            String category = user.getSelected_category();


            String endpoint = url + country +"&category="+category+"&apiKey=5f1d51ef33c24f7886a5598dd20974d2";
            String requestString = uid.toString();
            Long startTime = System.nanoTime();

            RestTemplate template = new RestTemplate();
            newsArticle response = template.getForObject(
                    url + country +"&category="+category+"&apiKey=5f1d51ef33c24f7886a5598dd20974d2",  // url to the api
                    newsArticle.class  // the expected response type is Article[]
            );

            Long endTime = System.nanoTime();
            String timetaken = String.valueOf((endTime - startTime));
            String responseString = String.valueOf(response);

            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = currentDateTime.format(formatter);

            Call call  = new Call(endpoint, requestString, responseString, timetaken, uid, formattedDateTime);
            cRepository.save(call);


            return response.getArticles();
        }
        throw new UserNotFoundException("User not found");


    }





    public List<Article> getNewsLimited(Long uid, Integer max_articles){
        String url = "https://newsapi.org/v2/top-headlines?country=";


        Optional<User> userOptional = uRepository.findById(uid);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String country = user.getSelected_country();
            String category = user.getSelected_category();


            String endpoint = url + country +"&category="+category+"&apiKey=5f1d51ef33c24f7886a5598dd20974d2&pageSize="+max_articles;
            String requestString = uid+" maxArticles="+max_articles;
            Long startTime = System.nanoTime();

            RestTemplate template = new RestTemplate();
            newsArticle response = template.getForObject(
                    url + country +"&category="+category+"&apiKey=5f1d51ef33c24f7886a5598dd20974d2&pageSize="+max_articles,  // url to the api
                    newsArticle.class  // the expected response type is Article[]
            );

            Long endTime = System.nanoTime();
            String timetaken = String.valueOf((endTime - startTime));
            String responseString = String.valueOf(response);

            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = currentDateTime.format(formatter);

            Call call  = new Call(endpoint, requestString, responseString, timetaken, uid, formattedDateTime);
            cRepository.save(call);


            return response.getArticles();
        }
        throw new UserNotFoundException("User not found");

    }



    public List<Source> getSources(Long uid){
        String url = "https://newsapi.org/v2/top-headlines/sources?country=";

        Optional<User> userOptional = uRepository.findById(uid);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String country = user.getSelected_country();
            String category = user.getSelected_category();


            String endpoint = url + country +"&category="+category+"&apiKey=5f1d51ef33c24f7886a5598dd20974d2";
            String requestString = uid.toString();
            Long startTime = System.nanoTime();

            RestTemplate template = new RestTemplate();
            ListSource response = template.getForObject(
                    url + country +"&category="+category+"&apiKey=5f1d51ef33c24f7886a5598dd20974d2",
                    ListSource.class
            );

            Long endTime = System.nanoTime();
            String timetaken = String.valueOf((endTime - startTime));
            String responseString = String.valueOf(response);

            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = currentDateTime.format(formatter);

            Call call  = new Call(endpoint, requestString, responseString, timetaken, uid, formattedDateTime);
            cRepository.save(call);


            return response.getSources();
        }
        throw new UserNotFoundException("User not found");


    }
//
//
    public ResponseEntity<?> addPrefSource(Long uid, String source_id){
        String url = "https://newsapi.org/v2/top-headlines/sources?country=";
        Optional<User> userOptional = uRepository.findById(uid);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String country = user.getSelected_country();
            String category = user.getSelected_category();


            String endpoint = url + country +"&category="+category+"&apiKey=5f1d51ef33c24f7886a5598dd20974d2";
            String requestString = uid.toString();
            Long startTime = System.nanoTime();

            RestTemplate template = new RestTemplate();
            ListSource response = template.getForObject(
                    url + country +"&category="+category+"&apiKey=5f1d51ef33c24f7886a5598dd20974d2",  // url to the api
                    ListSource.class // the expected response type is Article[]
            );

            Long endTime = System.nanoTime();
            String timetaken = String.valueOf((endTime - startTime));
            String responseString = String.valueOf(response);

            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = currentDateTime.format(formatter);

            Call call  = new Call(endpoint, requestString, responseString, timetaken, uid, formattedDateTime);
            cRepository.save(call);

            List<Source> sources = response.getSources();
            for(Source source : sources){
                if(source.getId().equals(source_id)){

                    PrefSources prefSource = new PrefSources(user, source_id);
                    sourceRepository.save(prefSource);

                    return new ResponseEntity<>(source_id, HttpStatus.OK);
                }
            }

            return null;

        }
        throw new UserNotFoundException("User not found");


    }



    public List<Source> sourceBasedNews(Long uid) {
        String url = "https://newsapi.org/v2/top-headlines/sources?country=";

        Optional<User> userOptional = uRepository.findById(uid);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String country = user.getSelected_country();
            String category = user.getSelected_category();


            String endpoint = url + country +"&category="+category+"&apiKey=5f1d51ef33c24f7886a5598dd20974d2";
            String requestString = uid.toString();
            Long startTime = System.nanoTime();

            RestTemplate template = new RestTemplate();
            ListSource response = template.getForObject(
                    url + country +"&category="+category+"&apiKey=5f1d51ef33c24f7886a5598dd20974d2",  // url to the api
                    ListSource.class // the expected response type is Article[]
            );

            Long endTime = System.nanoTime();
            String timetaken = String.valueOf((endTime - startTime));
            String responseString = String.valueOf(response);

            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = currentDateTime.format(formatter);

            Call call  = new Call(endpoint, requestString, responseString, timetaken, uid, formattedDateTime);
            cRepository.save(call);

            List<Source> all_articles = response.getSources();
            List<Source> pref_articles = new ArrayList<>();

            List<PrefSources> prefSources = sourceRepository.findAll();

            for(PrefSources prefSource : prefSources){
                for(Source article : all_articles) {
                    if (article.getId().equals(prefSource.getSource())) {
                        pref_articles.add(article);
                    }
                }
            }

            if(prefSources.size()==0)   return all_articles;
            else
                return pref_articles;
        }
        throw new UserNotFoundException("User not found");
    }


    public List<Article> newsByDate(Long uid, String from, String to){
        String url = "https://newsapi.org/v2/top-headlines?country=";

        Optional<User> userOptional = uRepository.findById(uid);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String country = user.getSelected_country();
            String category = user.getSelected_category();


            String endpoint = url + country +"&category="+category+"&apiKey=5f1d51ef33c24f7886a5598dd20974d2";
            String requestString = uid.toString();
            Long startTime = System.nanoTime();

            RestTemplate template = new RestTemplate();
            newsArticle response = template.getForObject(
                    url + country +"&category="+category+"&apiKey=5f1d51ef33c24f7886a5598dd20974d2",
                    newsArticle.class
            );

            Long endTime = System.nanoTime();
            String timetaken = String.valueOf((endTime - startTime));
            String responseString = String.valueOf(response);

            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = currentDateTime.format(formatter);


            Call call  = new Call(endpoint, requestString, responseString, timetaken, uid, formattedDateTime);
            cRepository.save(call);


            List<Article> all_articles = response.getArticles();
            List<Article> inDate_articles = new ArrayList<>();

            for(Article article : all_articles){
                if(article.getPublishedAt().substring(0,10).compareTo(from) >= 0 && article.getPublishedAt().substring(0,10).compareTo(to) <= 0){
                    inDate_articles.add(article);
                }
            }

            return inDate_articles;
        }
        throw new UserNotFoundException("User not found");

    }

    public ResponseEntity<?> sendNewsByEmail(Long uid) {
        String url = "https://newsapi.org/v2/top-headlines?country=";

        Optional<User> userOptional = uRepository.findById(uid);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String country = user.getSelected_country();
            String category = user.getSelected_category();
            String email = user.getEmail();

            String endpoint = url + country +"&category="+category+"&apiKey=5f1d51ef33c24f7886a5598dd20974d2";
            String requestString = uid.toString();
            Long startTime = System.nanoTime();


            RestTemplate template = new RestTemplate();
            newsArticle response = template.getForObject(
                    url + country + "&category=" + category + "&apiKey=5f1d51ef33c24f7886a5598dd20974d2",
                    newsArticle.class
            );

            Long endTime = System.nanoTime();
            String timetaken = String.valueOf((endTime - startTime));
            String responseString = String.valueOf(response);

            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = currentDateTime.format(formatter);


            Call call  = new Call(endpoint, requestString, responseString, timetaken, uid, formattedDateTime);
            cRepository.save(call);

            List<Article> all_articles = response.getArticles();
            String topHeadlines = "";
            for (Article article : all_articles) {
                topHeadlines += article.getTitle() + "\n" + article.getDescription() + "\n" + article.getUrl() + "\n\n";
            }

            String subject = "News for you";
            EmailRequest emailRequest = new EmailRequest(user.getEmail(), subject, topHeadlines);
            emailService.sendEmail(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getText());

            return ResponseEntity.ok("News sent successfully");

        }
        throw new UserNotFoundException("User not found");
    }

    public ResponseEntity<?> subscribe(Long uid){
        Optional<User> userOptional = uRepository.findById(uid);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Long user_id = user.getUniqueID();
            Optional<Subscription> subscriptionOptional = subscriptionRespository.findById(user_id);
            if(subscriptionOptional.isPresent()){
                Subscription subscription = subscriptionOptional.get();
                if(subscription.getIs_subscribed()){
                    return ResponseEntity.ok("Already subscribed");
                }
                else{
                    subscriptionRespository.subscribe(uid);
                    return ResponseEntity.ok("Subscribed successfully");
                }
            }
            subscriptionRespository.save(new Subscription(user_id, true));
            return ResponseEntity.ok("Subscribed successfully");
        }
        throw new UserNotFoundException("User not found");
    }

    public ResponseEntity<?> unsubscribe(Long uid){
        Optional<User> userOptional = uRepository.findById(uid);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Long user_id = user.getUniqueID();
            Optional<Subscription> subscriptionOptional = subscriptionRespository.findById(user_id);
            if(subscriptionOptional.isPresent()){
                Subscription subscription = subscriptionOptional.get();
                if(subscription.getIs_subscribed()){
                    subscriptionRespository.unSubscribe(uid);
                    return ResponseEntity.ok("Unsubscribed successfully");
                }
                else{
                    return ResponseEntity.ok("Already unsubscribed");
                }
            }
            subscriptionRespository.save(new Subscription(user_id, false));
            return ResponseEntity.ok("Unsubscribed successfully");
        }
        throw new UserNotFoundException("User not found");
    }



}
