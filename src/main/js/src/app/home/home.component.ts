import { Component } from '@angular/core';
import {Http, Response, Headers} from '@angular/http';

import {TranslateService} from 'ng2-translate';
import {AjaxResponse} from "../app.module";

declare let PNotify: any;

@Component({
  selector: 'home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
})
export class Home {

  private http: Http;
  private translate: TranslateService;
  private headers: Headers;
  public hideModal: boolean = false;

  constructor(translate: TranslateService, http: Http) {
    translate.use(translate.currentLang);
    this.translate = translate;
    this.http = http;
    this.headers = new Headers( {'Content-Type':'application/json'} );
  }

  public postAccount(login: string, password: string, email: string): void {
    this.http.post(
        "https://www.yildiz-games.be/api/v1/accounts/creations",
        '{"login":"' + login + '","password":"' + password + '","email":"' + email + '"}',
        {headers: this.headers}
     )
         .map( (res:Response) => res.json())
         .subscribe(
             data => this.handleResponse(data),
             err => this.handleError(err)
         );
  };

  public showDownload(): void {
      new PNotify({
    	title: this.translate.instant("index.download.button.notready.title"),
    	text:  this.translate.instant("index.download.button.notready.text"),
        type: "info"});
  };

  private handleResponse(response: AjaxResponse): void {
      this.hideModal = true;
      for( let i = 0; i < response.notifications.length; i++ ) {
      new PNotify({
        title: this.translate.instant(response.notifications[i].title),
        text:  this.translate.instant(response.notifications[i].content),
        type: response.notifications[i].type});
    }
  };

    private handleError(error: any): void {
        if( error.status === 0 ) {
            new PNotify({
                title: this.translate.instant("error.title"),
                text:  this.translate.instant("error.server.unavailable"),
                type: "error"});
        } else if( error.status === 422 || error.status === 500 ) {
            let errorContent: AjaxResponse = error.json();
            for( let i = 0; i < errorContent.notifications.length; i++ ) {
                new PNotify({
                    title: this.translate.instant(errorContent.notifications[i].title),
                    text:  this.translate.instant(errorContent.notifications[i].content),
                    type: errorContent.notifications[i].type});
            }
        } else {
            new PNotify({
                title: this.translate.instant("error.title"),
                text:  this.translate.instant("error.unknown"),
                type: "error"});
        }
    };

}
