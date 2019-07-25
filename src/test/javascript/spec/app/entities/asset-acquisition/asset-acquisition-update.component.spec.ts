/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { FixedAssetsTestModule } from '../../../test.module';
import { AssetAcquisitionUpdateComponent } from 'app/entities/asset-acquisition/asset-acquisition-update.component';
import { AssetAcquisitionService } from 'app/entities/asset-acquisition/asset-acquisition.service';
import { AssetAcquisition } from 'app/shared/model/asset-acquisition.model';

describe('Component Tests', () => {
  describe('AssetAcquisition Management Update Component', () => {
    let comp: AssetAcquisitionUpdateComponent;
    let fixture: ComponentFixture<AssetAcquisitionUpdateComponent>;
    let service: AssetAcquisitionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FixedAssetsTestModule],
        declarations: [AssetAcquisitionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AssetAcquisitionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AssetAcquisitionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AssetAcquisitionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AssetAcquisition(123);
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
        const entity = new AssetAcquisition();
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
