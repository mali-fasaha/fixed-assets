/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { FixedAssetsTestModule } from '../../../test.module';
import { CwipTransferUpdateComponent } from 'app/entities/cwip-transfer/cwip-transfer-update.component';
import { CwipTransferService } from 'app/entities/cwip-transfer/cwip-transfer.service';
import { CwipTransfer } from 'app/shared/model/cwip-transfer.model';

describe('Component Tests', () => {
  describe('CwipTransfer Management Update Component', () => {
    let comp: CwipTransferUpdateComponent;
    let fixture: ComponentFixture<CwipTransferUpdateComponent>;
    let service: CwipTransferService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FixedAssetsTestModule],
        declarations: [CwipTransferUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CwipTransferUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CwipTransferUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CwipTransferService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CwipTransfer(123);
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
        const entity = new CwipTransfer();
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
