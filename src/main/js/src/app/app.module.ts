import { BrowserModule } from '@angular/platform-browser';
import { Component, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TranslateService } from 'ng2-translate';
import { TranslateModule } from 'ng2-translate';

import { Home } from './home/home.component';
import { Profile } from './profile/profile.component';
import { Privacy } from './privacy/privacy.component';

declare let PNotify: any;
//declare let SockJS: any;
//declare let Stomp: any;

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})


export class App {

  translateService: TranslateService;

  constructor(translate: TranslateService) {
    this.translateService = translate;
    this.translateService.setDefaultLang('fr');
    this.setFrench();
    PNotify.prototype.options.styling = "bootstrap3";
    //this.connectWebsocket();
  }

  /*connectWebsocket() {
      console.log("Connecting to web socket");
      let socket = new SockJS("http://151.80.56.30:8080/yildiz-online-websocket");
      let stompClient = Stomp.over(socket);
      stompClient.connect({}, function (frame) {
          stompClient.subscribe("yildiz-online/public", function (message) {
              console.log("Message received:" + message);
          });
          stompClient.send("yildiz-online/public", {}, "message");
      });
  }*/

  setFrench() {
    this.translateService.use('français');
  }

  setEnglish() {
    this.translateService.use('english');
  }
}


@NgModule({
  declarations: [App, Home, Privacy, Profile],
  imports: [
    BrowserModule,
    TranslateModule.forRoot(),
    RouterModule.forRoot([
        { path: '', redirectTo: 'home', pathMatch: 'full' },
        { path: 'home', component: Home },
        { path: 'privacy', component: Privacy },
        { path: 'profile', component: Profile },
      ])],
  bootstrap: [App]
})
export class AppModule { }

export class Notification {

   private _title: string;

   private _content: string;

   private _type: string;

   get title(): string {
       return this._title;
   }

   get content(): string {
       return this._content;
   }

   get type(): string {
       return this._type;
   }
}

export class AjaxResponse {

   notifications: Notification[];
}
