/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { NBizEeTestModule } from '../../../test.module';
import { SaleInvoicePaymentUpdateComponent } from 'app/entities/sale-invoice-payment/sale-invoice-payment-update.component';
import { SaleInvoicePaymentService } from 'app/entities/sale-invoice-payment/sale-invoice-payment.service';
import { SaleInvoicePayment } from 'app/shared/model/sale-invoice-payment.model';

describe('Component Tests', () => {
    describe('SaleInvoicePayment Management Update Component', () => {
        let comp: SaleInvoicePaymentUpdateComponent;
        let fixture: ComponentFixture<SaleInvoicePaymentUpdateComponent>;
        let service: SaleInvoicePaymentService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NBizEeTestModule],
                declarations: [SaleInvoicePaymentUpdateComponent]
            })
                .overrideTemplate(SaleInvoicePaymentUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SaleInvoicePaymentUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SaleInvoicePaymentService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new SaleInvoicePayment(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.saleInvoicePayment = entity;
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
                    const entity = new SaleInvoicePayment();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.saleInvoicePayment = entity;
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
