/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { FixedAssetsTestModule } from '../../../test.module';
import { FixedAssetAssessmentUpdateComponent } from 'app/entities/fixed-asset-assessment/fixed-asset-assessment-update.component';
import { FixedAssetAssessmentService } from 'app/entities/fixed-asset-assessment/fixed-asset-assessment.service';
import { FixedAssetAssessment } from 'app/shared/model/fixed-asset-assessment.model';

describe('Component Tests', () => {
  describe('FixedAssetAssessment Management Update Component', () => {
    let comp: FixedAssetAssessmentUpdateComponent;
    let fixture: ComponentFixture<FixedAssetAssessmentUpdateComponent>;
    let service: FixedAssetAssessmentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FixedAssetsTestModule],
        declarations: [FixedAssetAssessmentUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(FixedAssetAssessmentUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FixedAssetAssessmentUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FixedAssetAssessmentService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new FixedAssetAssessment(123);
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
        const entity = new FixedAssetAssessment();
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
