import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { PaymentMethod } from 'app/shared/model/payment-method.model';
import { PaymentMethodService } from './payment-method.service';
import { PaymentMethodComponent } from './payment-method.component';
import { PaymentMethodDetailComponent } from './payment-method-detail.component';
import { PaymentMethodUpdateComponent } from './payment-method-update.component';
import { PaymentMethodDeletePopupComponent } from './payment-method-delete-dialog.component';
import { IPaymentMethod } from 'app/shared/model/payment-method.model';

@Injectable({ providedIn: 'root' })
export class PaymentMethodResolve implements Resolve<IPaymentMethod> {
    constructor(private service: PaymentMethodService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((paymentMethod: HttpResponse<PaymentMethod>) => paymentMethod.body));
        }
        return of(new PaymentMethod());
    }
}

export const paymentMethodRoute: Routes = [
    {
        path: 'payment-method',
        component: PaymentMethodComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'PaymentMethods'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'payment-method/:id/view',
        component: PaymentMethodDetailComponent,
        resolve: {
            paymentMethod: PaymentMethodResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PaymentMethods'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'payment-method/new',
        component: PaymentMethodUpdateComponent,
        resolve: {
            paymentMethod: PaymentMethodResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PaymentMethods'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'payment-method/:id/edit',
        component: PaymentMethodUpdateComponent,
        resolve: {
            paymentMethod: PaymentMethodResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PaymentMethods'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const paymentMethodPopupRoute: Routes = [
    {
        path: 'payment-method/:id/delete',
        component: PaymentMethodDeletePopupComponent,
        resolve: {
            paymentMethod: PaymentMethodResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PaymentMethods'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
