/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FixedAssetsTestModule } from '../../../test.module';
import { TransactionApprovalDetailComponent } from 'app/entities/transaction-approval/transaction-approval-detail.component';
import { TransactionApproval } from 'app/shared/model/transaction-approval.model';

describe('Component Tests', () => {
  describe('TransactionApproval Management Detail Component', () => {
    let comp: TransactionApprovalDetailComponent;
    let fixture: ComponentFixture<TransactionApprovalDetailComponent>;
    const route = ({ data: of({ transactionApproval: new TransactionApproval(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FixedAssetsTestModule],
        declarations: [TransactionApprovalDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TransactionApprovalDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TransactionApprovalDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.transactionApproval).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
