import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { CountUpOptions, CountUp } from 'countup.js';

@Component({
  selector: 'gha-dashboard-hud-orphaned-amortizations',
  templateUrl: './dashboard-hud-orphaned-amortizations.component.html',
  styleUrls: ['./dashboard-hud-orphaned-amortizations.component.scss']
})
export class DashboardHudOrphanedAmortizationsComponent implements OnInit {
  countUpOptions: CountUpOptions;
  modalRef: NgbModalRef;
  constructor() {}

  ngOnInit() {
    this.countUpOptions = {
      useEasing: true,
      startVal: 1000,
      decimalPlaces: 0,
      duration: 2,
      formattingFn: (n: number) => n.toPrecision(1).toString()
    };
  }
}
