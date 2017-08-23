import { Component } from '@angular/core';

import {TranslateService} from 'ng2-translate';

@Component({
  selector: 'privacy',
  templateUrl: 'privacy.component.html',
  styleUrls: ['privacy.component.css'],
})
export class Privacy {

  private translate: TranslateService;

  constructor(translate: TranslateService) {
    translate.use(translate.currentLang);
    this.translate = translate;
  }

}
