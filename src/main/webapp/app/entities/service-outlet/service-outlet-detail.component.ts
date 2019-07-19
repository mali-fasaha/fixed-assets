import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IServiceOutlet } from 'app/shared/model/service-outlet.model';

@Component({
  selector: 'gha-service-outlet-detail',
  templateUrl: './service-outlet-detail.component.html'
})
export class ServiceOutletDetailComponent implements OnInit {
  serviceOutlet: IServiceOutlet;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ serviceOutlet }) => {
      this.serviceOutlet = serviceOutlet;
    });
  }

  previousState() {
    window.history.back();
  }
}
