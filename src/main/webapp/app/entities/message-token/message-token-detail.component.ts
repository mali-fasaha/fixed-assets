import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMessageToken } from 'app/shared/model/message-token.model';

@Component({
  selector: 'gha-message-token-detail',
  templateUrl: './message-token-detail.component.html'
})
export class MessageTokenDetailComponent implements OnInit {
  messageToken: IMessageToken;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ messageToken }) => {
      this.messageToken = messageToken;
    });
  }

  previousState() {
    window.history.back();
  }
}
