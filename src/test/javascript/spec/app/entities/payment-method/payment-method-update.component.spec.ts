/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { NBizEeTestModule } from '../../../test.module';
import { PaymentMethodUpdateComponent } from 'app/entities/payment-method/payment-method-update.component';
import { PaymentMethodService } from 'app/entities/payment-method/payment-method.service';
import { PaymentMethod } from 'app/shared/model/payment-method.model';

describe('Component Tests', () => {
    describe('PaymentMethod Management Update Component', () => {
        let comp: PaymentMethodUpdateComponent;
        let fixture: ComponentFixture<PaymentMethodUpdateComponent>;
        let service: PaymentMethodService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NBizEeTestModule],
                declarations: [PaymentMethodUpdateComponent]
            })
                .overrideTemplate(PaymentMethodUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PaymentMethodUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PaymentMethodService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new PaymentMethod(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.paymentMethod = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new PaymentMethod();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.paymentMethod = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
