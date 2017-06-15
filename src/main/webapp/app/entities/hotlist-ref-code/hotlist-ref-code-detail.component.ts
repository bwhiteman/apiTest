import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { HotlistRefCode } from './hotlist-ref-code.model';
import { HotlistRefCodeService } from './hotlist-ref-code.service';

@Component({
    selector: 'jhi-hotlist-ref-code-detail',
    templateUrl: './hotlist-ref-code-detail.component.html'
})
export class HotlistRefCodeDetailComponent implements OnInit, OnDestroy {

    hotlistRefCode: HotlistRefCode;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private hotlistRefCodeService: HotlistRefCodeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInHotlistRefCodes();
    }

    load(id) {
        this.hotlistRefCodeService.find(id).subscribe((hotlistRefCode) => {
            this.hotlistRefCode = hotlistRefCode;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInHotlistRefCodes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'hotlistRefCodeListModification',
            (response) => this.load(this.hotlistRefCode.id)
        );
    }
}
