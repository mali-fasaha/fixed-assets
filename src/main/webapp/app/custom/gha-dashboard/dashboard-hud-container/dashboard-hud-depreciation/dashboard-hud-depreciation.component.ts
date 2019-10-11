import { Component, OnInit } from '@angular/core';
// import { DashboardHudAmortizationService } from 'app/preps/gha-dashboard/dashboard-hud-container/dashboard-hud-amortization/dashboard-hud-amortization.service';
// import { IBalanceQuery } from 'app/preps/model/balance-query.model';
import moment = require('moment');
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { CountUpOptions, CountUp } from 'countup.js';
import { NGXLogger } from 'ngx-logger';
import { NavigationExtras, Router } from '@angular/router';
// import { AmortizationScheduleModalService } from 'app/preps/data-display/data-tables/prepayment-amortization/amortization-schedule-modal.service';

@Component({
  selector: 'gha-dashboard-hud-depreciation',
  templateUrl: './dashboard-hud-depreciation.component.html',
  styleUrls: ['./dashboard-hud-depreciation.component.scss']
})
export class DashboardHudDepreciationComponent implements OnInit {
  depreciationAmount: number;
  countUpOptions: CountUpOptions;
  modalRef: NgbModalRef;

  constructor(private router: Router, private log: NGXLogger) // private amortizationScheduleModalService: AmortizationScheduleModalService,
  // private dashboardHudAmortizationService: DashboardHudAmortizationService
  {
    this.loadAll();
  }

  ngOnInit() {
    this.countUpOptions = {
      useEasing: true,
      startVal: 0,
      decimalPlaces: 3,
      duration: 2,
      formattingFn: (n: number) => n.toPrecision(4).toString()
    };
  }

  private loadAll(): void {
    // const balanceQuery: IBalanceQuery = {
    //   balanceDate: moment(),
    //   serviceOutlet: 'All',
    //   accountName: 'All'
    // };
    // this.dashboardHudAmortizationService.queryAmount(balanceQuery).subscribe(amount => {
    //   this.depreciationAmount = amount / 1000000;
    // });
  }

  protected navigateToMonth(): void {
    // this.modalRef = this.amortizationScheduleModalService.open();
  }

  protected navigateThisMonth(): void {
    const todate: string = moment().format('YYYY-MM-DD');
    const navigationExtras: NavigationExtras = {
      queryParams: {
        balanceDate: todate,
        serviceOutlet: 'All',
        accountName: 'All'
      }
    };

    this.router
      .navigate(['data-tables/amortization-schedule'], navigationExtras)
      .then(() => {
        this.log.debug(`Successfully navigated to amortization-schedule as at ${todate}`);
      })
      .catch(() => {
        this.log.debug(`This is embarrassing. The system has failed to navigate to amortization-schedule as at ${todate}`);
      });
  }
}
