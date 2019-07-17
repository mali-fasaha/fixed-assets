import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDealer } from 'app/shared/model/dealer.model';

@Component({
  selector: 'gha-dealer-detail',
  templateUrl: './dealer-detail.component.html'
})
export class DealerDetailComponent implements OnInit {
  dealer: IDealer;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ dealer }) => {
      this.dealer = dealer;
    });
  }

  previousState() {
    window.history.back();
  }
}
