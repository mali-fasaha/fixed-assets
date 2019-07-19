import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDepreciationRegime } from 'app/shared/model/depreciation-regime.model';

@Component({
  selector: 'gha-depreciation-regime-detail',
  templateUrl: './depreciation-regime-detail.component.html'
})
export class DepreciationRegimeDetailComponent implements OnInit {
  depreciationRegime: IDepreciationRegime;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ depreciationRegime }) => {
      this.depreciationRegime = depreciationRegime;
    });
  }

  previousState() {
    window.history.back();
  }
}
