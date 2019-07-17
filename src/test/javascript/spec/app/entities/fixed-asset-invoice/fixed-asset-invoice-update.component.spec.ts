/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { FixedAssetsTestModule } from '../../../test.module';
import { FixedAssetInvoiceUpdateComponent } from 'app/entities/fixed-asset-invoice/fixed-asset-invoice-update.component';
import { FixedAssetInvoiceService } from 'app/entities/fixed-asset-invoice/fixed-asset-invoice.service';
import { FixedAssetInvoice } from 'app/shared/model/fixed-asset-invoice.model';

describe('Component Tests', () => {
  describe('FixedAssetInvoice Management Update Component', () => {
    let comp: FixedAssetInvoiceUpdateComponent;
    let fixture: ComponentFixture<FixedAssetInvoiceUpdateComponent>;
    let service: FixedAssetInvoiceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FixedAssetsTestModule],
        declarations: [FixedAssetInvoiceUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(FixedAssetInvoiceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FixedAssetInvoiceUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FixedAssetInvoiceService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new FixedAssetInvoice(123);
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
        const entity = new FixedAssetInvoice();
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
