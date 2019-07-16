/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { FixedAssetsTestModule } from '../../../test.module';
import { AssetTransactionUpdateComponent } from 'app/entities/asset-transaction/asset-transaction-update.component';
import { AssetTransactionService } from 'app/entities/asset-transaction/asset-transaction.service';
import { AssetTransaction } from 'app/shared/model/asset-transaction.model';

describe('Component Tests', () => {
  describe('AssetTransaction Management Update Component', () => {
    let comp: AssetTransactionUpdateComponent;
    let fixture: ComponentFixture<AssetTransactionUpdateComponent>;
    let service: AssetTransactionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FixedAssetsTestModule],
        declarations: [AssetTransactionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AssetTransactionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AssetTransactionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AssetTransactionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AssetTransaction(123);
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
        const entity = new AssetTransaction();
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
