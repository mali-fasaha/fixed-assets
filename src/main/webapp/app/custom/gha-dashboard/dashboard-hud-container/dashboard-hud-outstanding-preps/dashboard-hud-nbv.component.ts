import { AfterViewInit, Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { NavigationExtras, Router } from '@angular/router';
import * as moment from 'moment';
import { NGXLogger } from 'ngx-logger';
import { CountUpOptions, CountUp } from 'countup.js';

@Component({
  selector: 'gha-dashboard-hud-nbv',
  templateUrl: './dashboard-hud-nbv.component.html',
  styleUrls: ['./dashboard-hud-nbv.component.scss']
})
export class DashboardHudNbvComponent implements OnInit {
  modalRef: NgbModalRef;
  outstandingBalanceAmount: number;
  countUpOptions: CountUpOptions;

  constructor(
    // private balanceQueryModalService: BalanceQueryModalService,
    // private outstandingBalanceService: OutstandingBalanceService,
    private router: Router,
    private log: NGXLogger
  ) {
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
    // this.outstandingBalanceService.queryAmount(balanceQuery).subscribe(balanceAmount => {
    //   this.outstandingBalanceAmount = balanceAmount / 1000000;
    // });
  }

  protected navigateToDate(): void {
    // this.modalRef = this.balanceQueryModalService.open();
  }

  protected navigateToDay(): void {
    const todate: string = moment().format('YYYY-MM-DD');
    const navigationExtras: NavigationExtras = {
      queryParams: {
        balanceDate: todate,
        serviceOutlet: 'All',
        accountName: 'All'
      }
    };

    this.router
      .navigate(['data-tables/prepayment-balances'], navigationExtras)
      .then(() => {
        this.log.debug(`Successfully navigated to prepayment-balances as at ${todate}`);
      })
      .catch(() => {
        this.log.debug(`This is embarrassing. The system has failed to navigate to prepayment-balances as at ${todate}`);
      });
  }
}
