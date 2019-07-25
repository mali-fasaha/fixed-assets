import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICwipTransfer } from 'app/shared/model/cwip-transfer.model';

@Component({
  selector: 'gha-cwip-transfer-detail',
  templateUrl: './cwip-transfer-detail.component.html'
})
export class CwipTransferDetailComponent implements OnInit {
  cwipTransfer: ICwipTransfer;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ cwipTransfer }) => {
      this.cwipTransfer = cwipTransfer;
    });
  }

  previousState() {
    window.history.back();
  }
}
