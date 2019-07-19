/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { FixedAssetsTestModule } from '../../../test.module';
import { DepreciationRegimeUpdateComponent } from 'app/entities/depreciation-regime/depreciation-regime-update.component';
import { DepreciationRegimeService } from 'app/entities/depreciation-regime/depreciation-regime.service';
import { DepreciationRegime } from 'app/shared/model/depreciation-regime.model';

describe('Component Tests', () => {
  describe('DepreciationRegime Management Update Component', () => {
    let comp: DepreciationRegimeUpdateComponent;
    let fixture: ComponentFixture<DepreciationRegimeUpdateComponent>;
    let service: DepreciationRegimeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FixedAssetsTestModule],
        declarations: [DepreciationRegimeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DepreciationRegimeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DepreciationRegimeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DepreciationRegimeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DepreciationRegime(123);
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
        const entity = new DepreciationRegime();
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
