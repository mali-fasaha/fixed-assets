import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICapitalWorkInProgress } from 'app/shared/model/capital-work-in-progress.model';

@Component({
  selector: 'gha-capital-work-in-progress-detail',
  templateUrl: './capital-work-in-progress-detail.component.html'
})
export class CapitalWorkInProgressDetailComponent implements OnInit {
  capitalWorkInProgress: ICapitalWorkInProgress;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ capitalWorkInProgress }) => {
      this.capitalWorkInProgress = capitalWorkInProgress;
    });
  }

  previousState() {
    window.history.back();
  }
}
