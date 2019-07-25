/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { FixedAssetsTestModule } from '../../../test.module';
import { TransactionApprovalUpdateComponent } from 'app/entities/transaction-approval/transaction-approval-update.component';
import { TransactionApprovalService } from 'app/entities/transaction-approval/transaction-approval.service';
import { TransactionApproval } from 'app/shared/model/transaction-approval.model';

describe('Component Tests', () => {
  describe('TransactionApproval Management Update Component', () => {
    let comp: TransactionApprovalUpdateComponent;
    let fixture: ComponentFixture<TransactionApprovalUpdateComponent>;
    let service: TransactionApprovalService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FixedAssetsTestModule],
        declarations: [TransactionApprovalUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TransactionApprovalUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TransactionApprovalUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TransactionApprovalService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TransactionApproval(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new TransactionApproval();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
